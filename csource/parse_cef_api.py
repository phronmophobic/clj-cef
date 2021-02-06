import sys
import os.path
import clang.cindex

from clang.cindex import CursorKind
from clang.cindex import TypeKind

from pprint import pprint
import json


def debug(*args):
    if False:
        sys.stdout.write(' '.join(map(str, args)))
        sys.stdout.write('\n')

def print_children(cur, level=1):
    for c in cur.get_children():
        debug('\t'*level, c.spelling, c.kind)
        print_children(c, level+1)

def get_type(t):
    if t.kind == TypeKind.POINTER:
        return [get_type(t.get_pointee())]
    else:
        if t.kind == TypeKind.ELABORATED:
            return t.get_declaration().spelling
        else:
            return t.spelling

def get_structs(cursor, filter_pred=None):
    '''pretty print cursor AST'''
        
    # level.show(cursor.kind, cursor.spelling, cursor.displayname, cursor.location)
    structs = {}
    
    if cursor.kind == CursorKind.TYPEDEF_DECL:

        
        for c in cursor.get_children():
            if c.kind == CursorKind.STRUCT_DECL:

                if filter_pred and filter_pred(c):
                    continue

                # c.location.file.name,
                debug ( cursor.kind, cursor.spelling, cursor.displayname)
                props = {}
                struct = {
                    'props': props,
                    'name': cursor.spelling,
                    'path': os.path.realpath(c.location.file.name)
                }
                structs[c.spelling] = struct

                # assuming fields
                for cc in c.get_children():
                    assert(cc.kind == CursorKind.FIELD_DECL), "Expecting only field decls"
                    if cc.type.kind == TypeKind.POINTER:
                        debug('\t\t', cc.spelling , cc.type.get_pointee().kind)
                    if cc.type.kind == TypeKind.POINTER and cc.type.get_pointee().kind == TypeKind.FUNCTIONPROTO:

                        # int (*foo) (int a, int b);
                        decl = '%s (*%s) (%s)' % ( cc.type.get_pointee().get_result().spelling,
                                                   cc.spelling, 
                                                   ', '.join(p.spelling for p in cc.type.get_pointee().argument_types()))

                        props[cc.spelling] = {
                            'type': 'fptr',
                            'name': cc.spelling,
                            'decl': decl,
                            'args': [get_type(p) for p in cc.type.get_pointee().argument_types()],
                            'ret': get_type(cc.type.get_pointee().get_result()),
                        }
                        # for p in cc.type.get_pointee().argument_types():
                        #     if p.kind == TypeKind.POINTER:
                        #         debug ('\t\t', p.get_pointee().get_declaration().lexical_parent.kind)
                        # debug('\t\t', cc.spelling, cc.type.get_pointee().get_result().spelling, [p.kind for p in cc.type.get_pointee().argument_types()])

                        # debug('\t\t',cc.type.get_pointee().kind)
                    else:
                        props[cc.spelling] = get_type(cc.type)

                    
    # if is_valid_type(cursor.type):
    #     show_type(cursor.type, 'type:')
    #     show_type(cursor.type.get_canonical(), 'canonical type:')

    for c in cursor.get_children():
        structs.update(get_structs(c, filter_pred))

    return structs


structs = {}

def is_subdir(suspect_child, suspect_parent):
    suspect_child = os.path.realpath(suspect_child)
    suspect_parent = os.path.realpath(suspect_parent)

    relative = os.path.relpath(suspect_child, start=suspect_parent)

    return not relative.startswith(os.pardir)

def matches_path(c, fname):
    cf = c.location.file

    return cf and is_subdir(c.location.file.name, fname)

if __name__ == '__main__':
    index = clang.cindex.Index.create()
    # index.parse(sys.argv[2])

    base_path = '/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64'
    fname = os.path.join(base_path, 'include/capi/cef_browser_capi.h')

    tu = index.parse('../csource/test.h', args = [
        ''.join(['-I', base_path]),
        # ''.join(['-I', '/Users/adrian/workspace/clj-cef/csource']),
        # '-framework', 'OpenGL',
        # '-framework', 'Cocoa',
        # '-framework', 'IOKit',
        # '-framework', 'CoreFoundation',
        # '-framework', 'CoreVideo',
        # '-framework', 'AppKit',
        # '-framework', 'CoreGraphics',
        # '-framework', 'CoreServices',
        # '-framework', 'Foundation',
        '-mmacosx-version-min=10.11',
        # "-dynamiclib",
        '-std=c++17',
    ])

    canonical_fname = os.path.realpath(fname)
    structs = get_structs(tu.cursor,
                          filter_pred=lambda c: matches_path(c, canonical_fname))

    for k,v in structs.items():
        v['path'] = os.path.relpath(v['path'], base_path)

    with open('../resources/cef.json', 'w') as fp:
        json.dump(structs, fp, indent=4, sort_keys=True)


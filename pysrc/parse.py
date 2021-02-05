# inspired by http://eli.thegreenplace.net/2011/07/03/parsing-c-in-python-with-clang/

import sys
import os.path
import clang.cindex

from clang.cindex import CursorKind
from clang.cindex import TypeKind

from pprint import pprint
import json

def verbose(*args, **kwargs):
    '''filter predicate for show_ast: show all'''
    return True
def no_system_includes(cursor):
    '''filter predicate for show_ast: filter out verbose stuff from system include files'''
    return (not cursor.location.file.name.startswith('/usr/include'))

# A function show(level, *args) would have been simpler but less fun
# and you'd need a separate parameter for the AST walkers if you want it to be exchangeable.
# class Level(int):
#     '''represent currently visited level of a tree'''
#     def show(self, *args):
#         '''pretty print an indented line'''
#         print ('\t'*self + ' '.join(map(str, args)))
#     def __add__(self, inc):
#         '''increase level'''
#         return Level(super(Level, self).__add__(inc))

def is_valid_type(t):
    '''used to check if a cursor has a type'''
    return t.kind != clang.cindex.TypeKind.INVALID
    
def qualifiers(t):
    '''set of qualifiers of a type'''
    q = set()
    if t.is_const_qualified(): q.add('const')
    if t.is_volatile_qualified(): q.add('volatile')
    if t.is_restrict_qualified(): q.add('restrict')
    return q

def show_type(t, title):
    '''pretty print type AST'''
    level.show(title, str(t.kind), ' '.join(qualifiers(t)))
    if is_valid_type(t.get_pointee()):
        show_type(t.get_pointee(), level+1, 'points to:')



def print_children(cur, level=1):
    


    for c in cur.get_children():



        print('\t'*level, c.spelling, c.kind)
        print_children(c, level+1)

def get_type(t):
    if t.kind == TypeKind.POINTER:
        return [get_type(t.get_pointee())]
    else:
        if t.kind == TypeKind.ELABORATED:
            return t.get_declaration().spelling
        else:
            return t.spelling

def show_ast(cursor, filter_pred=None):
    '''pretty print cursor AST'''
        
    # level.show(cursor.kind, cursor.spelling, cursor.displayname, cursor.location)
    structs = {}
    
    if cursor.kind == CursorKind.TYPEDEF_DECL:

        
        for c in cursor.get_children():
            if c.kind == CursorKind.STRUCT_DECL:

                if filter_pred and filter_pred(c):
                    continue

                # c.location.file.name,
                print ( cursor.kind, cursor.spelling, cursor.displayname)
                props = {}
                struct = {
                    'props': props,
                    'name': cursor.spelling,
                }
                structs[c.spelling] = struct

                # assuming fields
                for cc in c.get_children():
                    assert(cc.kind == CursorKind.FIELD_DECL), "Expecting only field decls"
                    if cc.type.kind == TypeKind.POINTER and cc.type.get_pointee().kind == TypeKind.FUNCTIONPROTO:
                        props[cc.spelling] = {
                            'type': 'fptr',
                            'name': cc.spelling,
                            'args': [get_type(p) for p in cc.type.get_pointee().argument_types()],
                            'ret': get_type(cc.type.get_pointee().get_result()),
                        }
                        for p in cc.type.get_pointee().argument_types():
                            if p.kind == TypeKind.POINTER:
                                print ('\t\t', p.get_pointee().get_declaration().lexical_parent.kind)
                        print('\t\t', cc.spelling, cc.type.get_pointee().get_result().spelling, [p.kind for p in cc.type.get_pointee().argument_types()])

                        # print('\t\t',cc.type.get_pointee().kind)
                    else:
                        props[cc.spelling] = get_type(cc.type)

                    
    # if is_valid_type(cursor.type):
    #     show_type(cursor.type, 'type:')
    #     show_type(cursor.type.get_canonical(), 'canonical type:')

    for c in cursor.get_children():
        structs.update(show_ast(c, filter_pred))

    return structs


structs = {}

def matches_path(c, fname):
    cf = c.location.file
    return cf and os.path.realpath(cf.name) != canonical_fname

if __name__ == '__main__':
    index = clang.cindex.Index.create()
    # index.parse(sys.argv[2])
    fname = sys.argv[1]
    tu = index.parse(fname, args = [
        '-I/Volumes/My Passport for Mac/backup/cef/cef',
        ])

    print ('Translation unit:', tu.spelling)
    # for f in tu.get_includes():
    #     print( '\t'*f.depth, f.include.name)
    canonical_fname = os.path.realpath(fname)
    structs = show_ast(tu.cursor,filter_pred=lambda c: matches_path(c, canonical_fname))
    print(json.dumps(structs, indent=4, sort_keys=True))


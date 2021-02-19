
#import <AppKit/AppKit.h>
#import <Cocoa/Cocoa.h>
#import <Foundation/Foundation.h>
#import <objc/runtime.h>

static NSString* changed_bundle_path;

@implementation NSBundle(changedBundle)
+ (void) changeBundlePath:(NSString*) bundlePath {
    changed_bundle_path = [bundlePath retain];

    Class originalClass = [NSBundle class];
    Method originalMeth = class_getClassMethod(originalClass, @selector(mainBundle));
    Method replacementMeth = class_getClassMethod([self class], @selector(changedBundle));
    method_exchangeImplementations(originalMeth, replacementMeth);
}

+ (NSBundle*) changedBundle {
    return [NSBundle bundleWithPath:changed_bundle_path];
}
@end



extern "C"{

    void change_bundle_path(const char* bundle_path){
        [NSBundle changeBundlePath:[NSString stringWithUTF8String:bundle_path]];
    }

void printDirectory(){
    NSBundle *main = [NSBundle mainBundle];
    NSString *resourcePath = [main bundlePath];
    NSLog(@"bundle dir: %@\n", resourcePath);
}

    // int main(int argc, char* argv[]) {
    //     printDirectory();
    // }
        
}


#import <AppKit/AppKit.h>
#import <Cocoa/Cocoa.h>
#import <Foundation/Foundation.h>
#import <objc/runtime.h>

@implementation NSBundle(changedBundle)
+ (void) highjack {
    Class originalClass = [NSBundle class];
    Method originalMeth = class_getClassMethod(originalClass, @selector(mainBundle));
    Method replacementMeth = class_getClassMethod([self class], @selector(changedBundle));
    method_exchangeImplementations(originalMeth, replacementMeth);
}

+ (NSBundle*) changedBundle {
    return [NSBundle bundleWithPath:@"/Users/adrian/workspace/clj-cef/csource"];
}
@end



extern "C"{

    void swizzle(){
        [NSBundle highjack];
    }

void printDirectory(){
    

        // NSArray *paths = NSSearchPathForDirectoriesInDomains(
        //     NSHomeDirectoryForUser,
        //     NSUserDomainMask, YES);
        // // NSString *basePath = [paths objectAtIndex:0];

        // for (NSString* path : paths){
        //     NSLog(@"path: %@\n", path);
        // }

    // NSLog(@"home: %@\n",  NSHomeDirectory());
        
        // Convert the NSString to a jstring
        // const char *cString = [basePath UTF8String];
        // fprintf(stderr, "%s", cString);


    NSFileManager* sharedFM = [NSFileManager defaultManager];

    NSArray* possibleURLs = [sharedFM URLsForDirectory:NSApplicationSupportDirectory

                                 inDomains:NSUserDomainMask];

    NSURL* appSupportDir = nil;

    NSURL* appDirectory = nil;

 

    if ([possibleURLs count] >= 1) {

        // Use the first directory (if multiple are returned)

        appSupportDir = [possibleURLs objectAtIndex:0];

    }

    NSLog(@"app support dir: %@\n", appSupportDir);

    // If a valid app support directory exists, add the

    // app's bundle ID to it to specify the final directory.

    if (appSupportDir) {

        NSString* appBundleID = [[NSBundle mainBundle] bundleIdentifier];
        if ( appBundleID){
            appDirectory = [appSupportDir URLByAppendingPathComponent:appBundleID];
        }

    }

    NSLog(@"app dir: %@\n", appDirectory);

    NSBundle *main = [NSBundle mainBundle];
    NSString *resourcePath = [main bundlePath];
    NSLog(@"bundle dir: %@\n", resourcePath);

        
}

    // int main(int argc, char* argv[]) {
    //     printDirectory();
    // }
        
}

#import "ClipboardListener.h"

@implementation ClipboardListener

NSString *const CLIPBOARD_TEXT_CHANGED = @"CLIPBOARD_TEXT_CHANGED";

RCT_EXPORT_MODULE();


- (NSArray<NSString *> *)supportedEvents
{
  return @[CLIPBOARD_TEXT_CHANGED];
}

- (void) listener:(NSNotification *) notification
{
    [self sendEventWithName:@"CLIPBOARD_TEXT_CHANGED" body:nil];
}

RCT_EXPORT_METHOD(setListener)
{
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(listener:) name:UIPasteboardChangedNotification object:nil];
}

RCT_EXPORT_METHOD(removeListener)
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

@end

import { NativeModules, NativeEventEmitter } from 'react-native';

const { ClipboardListener } = NativeModules;
const EVENT_NAME = 'CLIPBOARD_TEXT_CHANGED';

let eventListener;
const eventEmitter = new NativeEventEmitter(ClipboardListener);


const setListener = (callback) => {
  ClipboardListener.setListener()
  eventListener = eventEmitter.addListener('CLIPBOARD_TEXT_CHANGED', callback);
}
const removeListener = () => {
  eventEmitter.removeAllListeners(EVENT_NAME)
  ClipboardListener.removeListener()
}
export default {
  setListener,
  removeListener
}
import * as React from 'react';

import {StyleSheet, View, TextInput} from 'react-native';
import ClipboardListener from 'react-native-clipboard-listener';


export default function App() {
    const [value, setValue] = React.useState(null)

    const onClipboardChange = () => {
        alert("ClipboardChanged!")
    }

    React.useEffect(() => {
        ClipboardListener.setListener(onClipboardChange);

        return () => {
            ClipboardListener.removeListener();
        }
    }, []);

    return (
        <View style={styles.container}>
            <TextInput
                style={styles.input}
                onChangeText={setValue}
                value={value}
            />
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    input: {
        width: '90%', height: 40, backgroundColor: 'gray'
    },
});

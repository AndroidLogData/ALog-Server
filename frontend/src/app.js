import React from 'react';
import LogDataBox from './logdata';

class App extends React.Component {
    render() {
        return (
            <div>
                <LogDataBox url="/logdatalist"/>
            </div>
        );
    }
}

export default App;
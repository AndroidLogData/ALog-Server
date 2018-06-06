import React from 'react';
import LogData from './logdata';

class LevelFilter extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        let levelFilterUrl = 'http://localhost:8081/api/log-data/filter/level/query?package-name=' + this.props.match.params.packageName + '&level=' + this.props.match.params.level;

        return (
            <div>
                <LogData url={levelFilterUrl}/>
            </div>
        );
    }
}

export default LevelFilter;
import React from 'react';
import LogData from './logdata';

const LevelFilter = ({match}) => {
    let levelFilterUrl = '/log-data/filter/level/query?package-name=' + match.params.packageName + '&level=' + match.params.level;

    return (
        <div>
            <LogData url={levelFilterUrl}/>
        </div>
    );
};

export default LevelFilter;
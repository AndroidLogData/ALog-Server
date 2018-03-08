import React from 'react';
import LogData from './logdata';

const LevelFilter = ({match}) => {
    let levelFilterUrl = '/logdata/filter/level/query?packagename=' + match.params.packagename + '&level=' + match.params.level;

    return (
        <div>
            <LogData url={levelFilterUrl}/>
        </div>
    );
};

export default LevelFilter;
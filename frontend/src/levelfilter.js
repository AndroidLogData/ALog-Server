import React from 'react';
import LogData from './logdata';

const LevelFilter = ({match}) => {
    let levelFilterUrl = '/logdatalevelfilter/query?packagename=' + match.params.packagename + '&level=' + match.params.level;

    return (
        <div>
            <LogData url={levelFilterUrl}/>
        </div>
    );
};

export default LevelFilter;
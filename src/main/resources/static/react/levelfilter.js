import React from 'react';
import LogDataBox from './logdata';

const LevelFilter = ({ match }) => {
    let levelFilterUrl = "";

    if (match.params.level === 'v') {
        levelFilterUrl = '/logdatalevelfilter/query?level=v';
    } else if (match.params.level === 'i') {
        levelFilterUrl = '/logdatalevelfilter/query?level=i';
    } else if (match.params.level === 'd') {
        levelFilterUrl = '/logdatalevelfilter/query?level=d';
    } else if (match.params.level === 'w') {
        levelFilterUrl = '/logdatalevelfilter/query?level=w';
    } else if (match.params.level === 'e') {
        levelFilterUrl = '/logdatalevelfilter/query?level=e';
    }

    return (
        <div>
            <LogDataBox url={levelFilterUrl}/>
        </div>
    );
};

export default LevelFilter;
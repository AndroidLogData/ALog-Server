import React from 'react';
import LogData from './logdata';

const TagFilter = ({match}) => {
    let tagFilterUrl = '/logdatatagfilter/query?packagename=' + match.params.packagename + '&tag=' + match.params.tag;

    return (
        <div>
            <LogData url={tagFilterUrl}/>
        </div>
    );
};

export default TagFilter;
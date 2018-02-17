import React from 'react';
import LogData from './logdata';

const TagFilter = ({match}) => {
    let tagFilterUrl = '/logdatatagfilter/query?packagename=' + match.params.packagename + '&tag=' + match.params.tag;

    console.log(tagFilterUrl);

    return (
        <div>
            <LogData url={tagFilterUrl}/>
        </div>
    );
};

export default TagFilter;
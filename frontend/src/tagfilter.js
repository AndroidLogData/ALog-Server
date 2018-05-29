import React from 'react';
import LogData from './logdata';

const TagFilter = ({match}) => {
    let tagFilterUrl = '/log-data/filter/tag/query?package-name=' + match.params.packagename + '&tag=' + match.params.tag;

    return (
        <div>
            <LogData url={tagFilterUrl}/>
        </div>
    );
};

export default TagFilter;
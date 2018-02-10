import React from 'react';
import LogData from './logdata';

const TagFilter = ({ match }) => {
    let tagFilterUrl = '/logdatatagfilter/query?tag=' + match.params.tag;

    return (
        <div>
            <LogData url={tagFilterUrl}/>
        </div>
    );
};

export default TagFilter;
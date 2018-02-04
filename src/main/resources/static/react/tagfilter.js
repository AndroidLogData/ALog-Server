import React from 'react';
import LogDataBox from './logdata';

const TagFilter = ({ match }) => {
    let tagFilterUrl = '/logdatatagfilter/query?tag=' + match.params.tag;

    return (
        <div>
            <LogDataBox url={tagFilterUrl}/>
        </div>
    );
};

export default TagFilter;

//http://localhost:3000/logdatatagfilter/query?tag=%5BHello::%3Cinit%3E%5D
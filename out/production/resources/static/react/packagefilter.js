import React from 'react';
import LogDataBox from './logdata';

const PackageFilter = ({ match }) => {
    let packageFilterUrl = '/logdatapackagenamefilter/query?packagename=' + match.params.packagename;

    console.log(packageFilterUrl);

    return (
        <div>
            <LogDataBox url={packageFilterUrl}/>
        </div>
    );
};

export default PackageFilter;
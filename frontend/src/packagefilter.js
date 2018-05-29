import React from 'react';
import LogData from './logdata';

const PackageFilter = ({match}) => {
    let packageFilterUrl = '/log-data/filter/package-name/query?package-name=' + match.params.packageName;

    return (
        <div>
            <LogData url={packageFilterUrl}/>
        </div>
    );
};

export default PackageFilter;
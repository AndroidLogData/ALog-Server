import React from 'react';
import LogData from './logdata';

class PackageFilter extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        let packageFilterUrl = '/log-data/filter/package-name/query?package-name=' + this.props.match.params.packageName;

        return (
            <div>
                <LogData url={packageFilterUrl}/>
            </div>
        );
    }
}

export default PackageFilter;
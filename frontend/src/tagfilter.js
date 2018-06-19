import React from 'react';
import LogData from './logdata';

class TagFilter extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        let tagFilterUrl = 'http://localhost:8081/api/log-data/filter/tag/query?package-name=' + this.props.match.params.packageName + '&tag=' + this.props.match.params.tag;

        return (
            <div>
                <LogData url={tagFilterUrl}/>
            </div>
        );
    }
}

export default TagFilter;
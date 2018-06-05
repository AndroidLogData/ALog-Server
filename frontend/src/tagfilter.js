import React from 'react';
import LogData from './logdata';

class TagFilter extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        let tagFilterUrl = '/log-data/filter/tag/query?package-name=' + this.props.match.params.packageName + '&tag=' + this.props.match.params.tag;

        return (
            <div>
                <LogData url={tagFilterUrl}/>
            </div>
        );
    }
}

export default TagFilter;
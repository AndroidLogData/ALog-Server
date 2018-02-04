import React from 'react';
import {
    Link
} from 'react-router-dom'

class FilterBar extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div className="panel panel-default">
                <div className="panel-heading">
                    <h3 className="panel-title">Filter</h3>
                </div>
                <div className="panel-body">
                    <div className="btn-group">
                        <button type="button" className="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                aria-expanded="false">
                            Level <span className="caret"/>
                        </button>
                        <ul className="dropdown-menu" role="menu">
                            <li><Link to="/levelfilter/i">i</Link></li>
                            <li><Link to="/levelfilter/d">d</Link></li>
                            <li><Link to="/levelfilter/w">w</Link></li>
                            <li><Link to="/levelfilter/v">v</Link></li>
                            <li><Link to="/levelfilter/e">e</Link></li>
                        </ul>
                    </div>
                    <TagList/>
                    <PackageNameList/>
                </div>
            </div>
        );
    }
}

class TagList extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            logData: []
        };

        this.fetchTagData();
    }

    fetchTagData() {
        $.ajax({
            url: '/tagdatalist',
            dataType: 'json',
            cache: false,
            success: function (data) {
                this.setState({logData: data.logData});
            }.bind(this),
            error: function (xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    }

    componentDidMount() {
        this.fetchTagData();
    }

    shouldComponentUpdate() {
        this.fetchTagData();
        return true;
    }

    componentWillReceiveProps() {
        this.fetchTagData();
    }

    render() {
        let i;
        let tagNodes = [];

        for (i = 0; i < this.state.logData.length; i++) {
            tagNodes.push(
                <li><Link to={{pathname: '/tagfilter/' + this.state.logData[i]}}>{this.state.logData[i]}</Link></li>
            );
        }

        return (
            <div className="btn-group">
                <button type="button" className="btn btn-default dropdown-toggle" data-toggle="dropdown"
                        aria-expanded="false">
                    Tag <span className="caret"/>
                </button>
                <ul className="dropdown-menu" role="menu">
                    {tagNodes}
                </ul>
            </div>
        );
    }
}

class PackageNameList extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            logData: []
        };

        this.fetchPackageNameData();
    }

    fetchPackageNameData() {
        $.ajax({
            url: '/packagenamedatalist',
            dataType: 'json',
            cache: false,
            success: function (data) {
                this.setState({logData: data.logData});
            }.bind(this),
            error: function (xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    }

    componentDidMount() {
        this.fetchPackageNameData();
    }

    shouldComponentUpdate() {
        this.fetchPackageNameData();
        return true;
    }

    componentWillReceiveProps() {
        this.fetchPackageNameData();
    }

    render() {
        let i;
        let packageNameNodes = [];

        for (i = 0; i < this.state.logData.length; i++) {
            packageNameNodes.push(
                <li><Link to={{pathname: '/packagenamefilter/' + this.state.logData[i]}}>{this.state.logData[i]}</Link></li>
            );
        }

        return (
            <div className="btn-group">
                <button type="button" className="btn btn-default dropdown-toggle" data-toggle="dropdown"
                        aria-expanded="false">
                    Package Name <span className="caret"/>
                </button>
                <ul className="dropdown-menu" role="menu">
                    {packageNameNodes}
                </ul>
            </div>
        );
    }
}

export default FilterBar;
import React from 'react';
import {
    Link
} from 'react-router-dom';
import $ from 'jquery';

class FilterBar extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div>
                <div className="card">
                    <h3 className="card-header">Filter</h3>
                    <div className="card-block">
                        <div className="btn-group">
                            <button type="button" className="btn btn-secondary dropdown-toggle" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false">
                                Level
                            </button>
                            <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <Link className="dropdown-item" to="/levelfilter/i">i</Link>
                                <Link className="dropdown-item" to="/levelfilter/d">d</Link>
                                <Link className="dropdown-item" to="/levelfilter/w">w</Link>
                                <Link className="dropdown-item" to="/levelfilter/v">v</Link>
                                <Link className="dropdown-item" to="/levelfilter/e">e</Link>
                            </div>
                        </div>
                        <TagList/>
                        <PackageNameList/>
                    </div>
                </div>
                <br/>
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

    shouldComponentUpdate(nextProps, nextState) {
        this.fetchTagData();
        return nextState.length !== this.state.logData.length;
    }

    render() {
        let i;
        let tagNodes = [];

        for (i = 0; i < this.state.logData.length; i++) {
            tagNodes.push(
                <Link className="dropdown-item"
                      to={{pathname: '/tagfilter/' + this.state.logData[i]}}>{this.state.logData[i]}</Link>
            );
        }

        return (
            <div className="btn-group">
                <button type="button" className="btn btn-secondary dropdown-toggle" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                    Tag
                </button>
                <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    {tagNodes}
                </div>
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

    shouldComponentUpdate(nextProps, nextState) {
        this.fetchPackageNameData();
        return nextState.length !== this.state.logData.length;
    }

    render() {
        let i;
        let packageNameNodes = [];

        for (i = 0; i < this.state.logData.length; i++) {
            packageNameNodes.push(
                <Link className="dropdown-item"
                      to={{pathname: '/packagenamefilter/' + this.state.logData[i]}}>{this.state.logData[i]}</Link>
            );
        }

        return (
            <div className="btn-group">
                <button type="button" className="btn btn-secondary dropdown-toggle" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                    PackageName
                </button>
                <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    {packageNameNodes}
                </div>
            </div>
        );
    }
}

export default FilterBar;
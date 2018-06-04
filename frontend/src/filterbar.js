import React from 'react';
import {
    Link
} from 'react-router-dom';
import $ from 'jquery';

class FilterBar extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <div className="card">
                    <h3 className="card-header">Filter</h3>
                    <div className="card-block">
                        <div className="btn-group" style={{marginRight: 0.5 + '%'}}>
                            <button type="button" className="btn btn-secondary dropdown-toggle" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false">
                                Level
                            </button>
                            <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <Link className="dropdown-item"
                                      to={{pathname: '/log-data/filter/level/' + this.props.packageName + '/v'}}>v</Link>
                                <Link className="dropdown-item"
                                      to={{pathname: '/log-data/filter/level/' + this.props.packageName + '/i'}}>i</Link>
                                <Link className="dropdown-item"
                                      to={{pathname: '/log-data/filter/level/' + this.props.packageName + '/d'}}>d</Link>
                                <Link className="dropdown-item"
                                      to={{pathname: '/log-data/filter/level/' + this.props.packageName + '/w'}}>w</Link>
                                <Link className="dropdown-item"
                                      to={{pathname: '/log-data/filter/level/' + this.props.packageName + '/e'}}>e</Link>
                            </div>
                        </div>
                        <TagList packageName={this.props.packageName}/>
                        {/*<PackageNameList packageName={this.props.packageName}/>*/}
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
            url: '/log-data/tag/set/query?',
            type: "GET",
            dataType: 'json',
            data: {"package-name": this.props.packageName},
            cache: false,
            success: function (data) {
                this.setState({logData: data});
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
                      to={{pathname: '/log-data/filter/tag/' + this.props.packageName + '/' + this.state.logData[i]}}>{this.state.logData[i]}</Link>
            );
        }

        return (
            <div className="btn-group" style={{marginRight: 0.5 + '%'}}>
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

// class PackageNameList extends React.Component {
//     constructor(props) {
//         super(props);
//
//         this.state = {
//             logData: []
//         };
//
//         this.fetchPackageNameData();
//     }
//
//     fetchPackageNameData() {
//         $.ajax({
//             url: '/log-data/package-name/set',
//             dataType: 'json',
//             cache: false,
//             success: function (data) {
//                 this.setState({logData: data});
//             }.bind(this),
//             error: function (xhr, status, err) {
//                 console.error(this.props.url, status, err.toString());
//             }.bind(this)
//         });
//     }
//
//     shouldComponentUpdate(nextProps, nextState) {
//         this.fetchPackageNameData();
//         return nextState.length !== this.state.logData.length;
//     }
//
//     render() {
//         let i;
//         let packageNameNodes = [];
//
//         for (i = 0; i < this.state.logData.length; i++) {
//             packageNameNodes.push(
//                 <Link className="dropdown-item"
//                       to={{pathname: '/log-data/filter/package-name/' + this.state.logData[i]}}>{this.state.logData[i]}</Link>
//             );
//         }
//
//         return (
//             <div className="btn-group">
//                 <button type="button" className="btn btn-secondary dropdown-toggle" data-toggle="dropdown"
//                         aria-haspopup="true" aria-expanded="false">
//                     PackageName
//                 </button>
//                 <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
//                     {packageNameNodes}
//                 </div>
//             </div>
//         );
//     }
// }

export default FilterBar;
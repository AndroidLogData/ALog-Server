import React from 'react';
import LevelFilter from "./levelfilter";
import TagFilter from "./tagfilter";
import PackageFilter from "./packagefilter";
import {Route} from 'react-router-dom';
import $ from "jquery";

class App extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            apiKey: ""
        };
    }

    componentDidMount() {
        $.ajax({
            url: "/login/user",
            type: "GET",
            dataType: "json",
            cache: false,
            success: function (data) {
                this.setState({apiKey: data["apiKey"]});
            }.bind(this),
            error: function (xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    }

    render() {
        return (
            <div>
                <Route path="/log-data/filter/level/:packageName/:level"
                       component={LevelFilter}/>
                <Route path="/log-data/filter/tag/:packageName/:tag"
                       component={TagFilter}/>
                <Route path="/log-data/filter/package-name/:packageName"
                       component={PackageFilter}/>
            </div>
        );
    }
}

export default App;
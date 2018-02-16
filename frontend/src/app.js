import React from 'react';
import LogData from './logdata';
import LevelFilter from "./levelfilter";
import TagFilter from "./tagfilter";
import PackageFilter from "./packagefilter";
import {Route} from 'react-router-dom';
import SideMenu from './sidemenu';
import MainPage from './main';

class App extends React.Component {
    render() {
        return (
            <div>
                <SideMenu/>
                <Route exact path="/" component={MainPage}/>
                <Route path="/levelfilter/:packagename/:level" component={LevelFilter}/>
                <Route path="/tagfilter/:packagename/:tag" component={TagFilter}/>
                <Route path="/packagenamefilter/:packagename" component={PackageFilter}/>
            </div>
        );
    }
}

export default App;
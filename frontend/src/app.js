import React from 'react';
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
                {/*<SideMenu/>*/}
                <Route exact path="/" component={MainPage}/>
                <Route path="/logdata/filter/level/:packagename/:level" component={LevelFilter}/>
                <Route path="/logdata/filter/tag/:packagename/:tag" component={TagFilter}/>
                <Route path="/logdata/filter/packagename/:packagename" component={PackageFilter}/>
            </div>
        );
    }
}

export default App;
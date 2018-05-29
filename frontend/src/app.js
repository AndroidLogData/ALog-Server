import React from 'react';
import LevelFilter from "./levelfilter";
import TagFilter from "./tagfilter";
import PackageFilter from "./packagefilter";
import {Route} from 'react-router-dom';

class App extends React.Component {
    render() {
        return (
            <div>
                <Route path="/log-data/filter/level/:packageName/:level" component={LevelFilter}/>
                <Route path="/log-data/filter/tag/:packageName/:tag" component={TagFilter}/>
                <Route path="/log-data/filter/package-name/:packageName" component={PackageFilter}/>
            </div>
        );
    }
}

export default App;
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import './App.css';
import AlbumAnalysis from './components/AlbumAnalysis';
import ArtistAnalysis from './components/ArtistAnalysis';
import Landing from './components/Landing';
import TrackAnalysis from './components/TrackAnalysis';

function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/" exact={true} component={Landing}></Route>
        <Route path="/tracks" exact={true} component={TrackAnalysis}></Route>
        <Route path="/artists" exact={true} component={ArtistAnalysis}></Route>
        <Route path="/albums" exact={true} component={AlbumAnalysis}></Route>
      </Switch>
    </BrowserRouter>
  );
}

export default App;

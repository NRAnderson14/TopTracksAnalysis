import { BrowserRouter, Route, Switch } from 'react-router-dom';
import './App.css';
import Landing from './components/Landing';

function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/" exact={true} component={Landing}></Route>
      </Switch>
    </BrowserRouter>
  );
}

export default App;

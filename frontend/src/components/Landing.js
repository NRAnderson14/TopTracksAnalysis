import { Link } from 'react-router-dom';

const { Component } = require("react");

class Landing extends Component {

  async componentDidMount() {
    document.title = "Top Tracks Analysis";

    const search = this.props.location.search;
    const code = new URLSearchParams(search).get("code");
    const state = new URLSearchParams(search).get("state");
    const error = new URLSearchParams(search).get("error");

    if (code != null || error != null) {
      let params = "?";
      params += code == null ? "" : "code=" + code;
      params += state == null ? "" : "state=" + state;
      params += error == null ? "" : "error=" + error;
      await fetch("/receive_code" + params).then(response => {
        if (response.status !== 200) {
          console.error("Unable to authorize user");
        }
      });
    } else {
      await fetch("/login").then(response => {
        response.text().then(url => window.location.href = url);
      });
    }

  }

  render() {
    return (
      <div id="landing">
        <h1>Top Tracks Analysis</h1>
        <div>
          <div className="analysis-link">
            <h4><Link to="/tracks">Tracks</Link></h4>
          </div>
          <div className="analysis-link">
            <h4><Link to="/artists">Artists</Link></h4>
          </div>
          <div className="analysis-link">
            <h4><Link to="/albums">Albums</Link></h4>
          </div>
        </div>
      </div>
    );
  }
}

export default Landing;
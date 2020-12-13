const { Component } = require("react");
const { default: TrackList } = require("./TrackList");

class Landing extends Component {
  
  async componentDidMount() {
    document.title = "Top Tracks Analysis";
  }

  render() {
    return (
      <div id="landing">
        <h1>Top Tracks Analysis</h1>
        <TrackList />
      </div>
    );
  }
}

export default Landing;
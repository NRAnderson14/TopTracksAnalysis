const { Component } = require("react");
const { default: Track } = require("./Track");

class TrackList extends Component {

  state = {
    isLoading: true,
    trackList: []
  };

  async componentDidMount() {
    const response = await fetch("/user/playlists/recaps/multi_occurrences?type=track");
    const body = await response.json();
    this.setState({ trackList: body, isLoading: false });
  }

  render() {
    return this.state.isLoading ?
      (
        <div className="track-list">
          <h3>Loading..</h3>
        </div>
      ) :
      (
        <div id="track-list">
          {this.state.trackList.map((trackAnalysisResult, trackNumber) =>
            <Track trackAnalysisResult={trackAnalysisResult} trackNumber={trackNumber + 1} />
          )}
        </div>
      );
  }
}

export default TrackList;
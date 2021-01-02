import Artist from "./Artist";

const { Component } = require("react");
const { default: Track } = require("./Track");

class ArtistList extends Component {

  state = {
    isLoading: true,
    artistList: []
  };

  async componentDidMount() {
    const response = await fetch("/user/playlists/recaps/multi_occurrences?type=artist");
    const body = await response.json();
    this.setState({ artistList: body, isLoading: false });
  }

  render() {
    return this.state.isLoading ?
      (
        <div className="artist-list">
          <h3>Loading..</h3>
        </div>
      ) :
      (
        <div id="artist-list">
          {this.state.artistList.map((artistAnalysisResult, artistNumber) =>
            <Artist artistAnalysisResult={artistAnalysisResult} artistNumber={artistNumber + 1} />
          )}
        </div>
      );
  }
}

export default ArtistList;
import Album from "./Album";

const { Component } = require("react");
const { default: Track } = require("./Track");

class AlbumList extends Component {

  state = {
    isLoading: true,
    albumList: []
  };

  async componentDidMount() {
    const response = await fetch("/user/playlists/recaps/multi_occurrences?type=album");
    const body = await response.json();
    this.setState({ albumList: body, isLoading: false });
  }

  render() {
    return this.state.isLoading ?
      (
        <div className="album-list">
          <h3>Loading..</h3>
        </div>
      ) :
      (
        <div id="album-list">
          {this.state.albumList.map((albumAnalysisResult, albumNumber) =>
            <Album albumAnalysisResult={albumAnalysisResult} albumNumber={albumNumber + 1} />
          )}
        </div>
      );
  }
}

export default AlbumList;
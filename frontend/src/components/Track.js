import Image from "./Image";
import TrackStatistics from "./TrackStatistics";

const { Component } = require("react");

class Track extends Component {
  stats = {
    occurrences: this.props.trackAnalysisResult.occurrences,
    firstAppearance: this.props.trackAnalysisResult.first_appearance,
    latestAppearance: this.props.trackAnalysisResult.latest_appearance,
    appearances: this.props.trackAnalysisResult.appearances,
    averagePosition: this.props.trackAnalysisResult.average_position
  };

  render() {
    return (
      <div className="track">
        <p>{this.props.trackNumber}</p>
        <Image href={this.props.trackAnalysisResult.track.album.images[2].url} alt={this.props.trackAnalysisResult.track.album.name} />
        <div className="track-info">
          <p>{this.props.trackAnalysisResult.track.name}</p>
          <p>{this.props.trackAnalysisResult.track.artists[0].name}</p>
        </div>
        <TrackStatistics stats={this.stats} />
      </div>
    );
  }
}

export default Track;
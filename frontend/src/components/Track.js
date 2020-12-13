import Image from "./Image";
import TrackStatistics from "./TrackStatistics";

const { Component } = require("react");

class Track extends Component {
  result = this.props.trackAnalysisResult;
  track = this.result.track;
  stats = {
    occurrences: this.result.occurrences,
    firstAppearance: this.result.first_appearance,
    latestAppearance: this.result.latest_appearance,
    appearances: this.result.appearances,
    averagePosition: this.result.average_position
  };

  render() {
    return (
      <div className="track">
        <p className="track-number">{this.props.trackNumber}</p>
        <Image href={this.track.album.images[2].url} alt={this.track.album.name} />
        <div className="track-info">
          <p><b>{this.track.name}</b></p>
          <p>{this.track.artists[0].name}</p>
        </div>
        <TrackStatistics stats={this.stats} />
      </div>
    );
  }
}

export default Track;
import Image from "./Image";
import TrackStatistics from "./TrackStatistics";

const { Component } = require("react");

class Track extends Component {
  getYear = function (dateString) {
    return new Date(dateString).getFullYear();
  }

  result = this.props.trackAnalysisResult;
  track = this.result.analysis_item; // Presumably being passed the correct type
  stats = {
    occurrences: this.result.occurrences,
    firstAppearance: this.result.first_appearance,
    latestAppearance: this.result.latest_appearance,
    appearances: this.result.appearances.map(this.getYear),
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
        <TrackStatistics stats={this.stats} dateFormatter={this.getYear} />
      </div>
    );
  }
}

export default Track;
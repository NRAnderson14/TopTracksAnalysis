import Image from "./Image";
import TrackStatistics from "./TrackStatistics";

const { Component } = require("react");

class Album extends Component {
  getYear = function (dateString) {
    return new Date(dateString).getFullYear();
  }

  collectAppearances = function (appearances) {
    var aps2 = new Map();
    var aps = appearances.map(this.getYear);
    for (const year of aps) {
      if (aps2.has(year)) {
        var count = aps2.get(year)
        aps2.set(year, count + 1)
      } else {
        aps2.set(year, 1);
      }
    }

    var ret = []
    for (const [key, value] of aps2) {
      ret.push(key + " x " + value);
    }
    return ret;
  }

  result = this.props.albumAnalysisResult;
  album = this.result.analysis_item; // Presumably being passed the correct type
  stats = {
    occurrences: this.result.occurrences,
    firstAppearance: this.result.first_appearance,
    latestAppearance: this.result.latest_appearance,
    appearances: this.collectAppearances(this.result.appearances),
    averagePosition: this.result.average_position
  };

  render() {
    return (
      <div className="album">
        <p className="album-number">{this.props.albumNumber}</p>
        <Image href={this.album.images[2].url} alt={this.album.name} />
        <div className="album-info">
          <p><b>{this.album.name}</b></p>
          <p>{this.album.artists[0].name}</p>
        </div>
        <TrackStatistics stats={this.stats} dateFormatter={this.getYear} />
      </div>
    );
  }
}

export default Album;
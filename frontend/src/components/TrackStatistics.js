const { Component } = require("react");

class TrackStatistics extends Component {

  render() {
    return (
      <div className="track-statistics">
        <p className="stat">Occurrences: {this.props.stats.occurrences}</p>
        <div className="stat">
          <p>First Appearance: {this.props.stats.firstAppearance}</p>
          <p>Latest Appearance: {this.props.stats.latestAppearance}</p>
        </div>
        <div className="stat">
          <p>All Appearances</p>
          <ul>
            {this.props.stats.appearances.map(appearance =>
              <li>{appearance}</li>
            )}
          </ul>
        </div>
        <p className="stat">Average Position: {this.props.stats.averagePosition}</p>
      </div>
    );
  }
}

export default TrackStatistics;
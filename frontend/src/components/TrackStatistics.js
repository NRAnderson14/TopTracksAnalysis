const { Component } = require("react");

class TrackStatistics extends Component {

  formatAllAppearances = (appearances) => {
    let years = appearances.sort();
    let contiguousAppearances = [];
    let block = [];
    block.push(years[0]);
    for (var i = 1; i < years.length; i++) {
      if (years[i] - 1 == block[block.length-1]) {
        block.push(years[i]);
      } else {
        contiguousAppearances.push(block);
        block = [];
        block.push(years[i]);
      }
    }
    contiguousAppearances.push(block);

    let appearanceStrings = [];
    contiguousAppearances.forEach(appBlock => {
      if (appBlock.length == 1) {
        appearanceStrings.push(appBlock[0]);
      } else {
        appearanceStrings.push(appBlock[0] + "-" + appBlock[appBlock.length-1]);
      }
    });

    return appearanceStrings.join(", ");
  }

  render() {
    return (
      <div className="track-statistics">
        <div className="stat">
          <h4>Appearances</h4>
          <p>Total: {this.props.stats.occurrences}</p>
          <p>First: {this.props.dateFormatter(this.props.stats.firstAppearance)}</p>
          <p>Last: {this.props.dateFormatter(this.props.stats.latestAppearance)}</p>
          <p>All: {this.formatAllAppearances(this.props.stats.appearances)}</p>
        </div>
        {/* <div className="stat">
          <p>First Appearance: {this.props.dateFormatter(this.props.stats.firstAppearance)}</p>
          <p>Latest Appearance: {this.props.dateFormatter(this.props.stats.latestAppearance)}</p>
        </div> */}
        {/* <div className="stat">
          <p>All Appearances</p>
          <ul>
            {this.props.stats.appearances.map(appearance =>
              <li>{appearance}</li>
            )}
          </ul>
        </div> */}
        <div className="stat">
          <p>Average Position: {this.props.stats.averagePosition}</p>
        </div>
      </div>
    );
  }
}

export default TrackStatistics;
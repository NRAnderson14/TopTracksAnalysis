const { Component } = require("react");

class Image extends Component {

  render() {
    return (
      <img src={this.props.href} alt={this.props.alt} className="album-image"></img>
    );
  }
}

export default Image;
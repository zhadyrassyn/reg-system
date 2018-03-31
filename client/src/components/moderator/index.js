import React, {Component} from 'react'

class ModeratorApp extends Component {

  componentWillMount() {
    document.body.style.backgroundColor = "#F8F6F9";
  }
  componentWillUnmount() {
    document.body.style.backgroundColor = null;
  }

  render() {
    return (
      <div className="wrapper">
        <nav className="navbar justify-content-between py-4">
          <div className="container">
            <form className="form-inline" id="search-form">
              <input type="text" className="form-control" placeholder="Search..."/>
              <button type="button" className="btn btn-primary ml-2">Search</button>
            </form>
            <div>
              <a href="#">Export xls</a>
            </div>
          </div>
        </nav>
      </div>

    )
  }
}

export default ModeratorApp
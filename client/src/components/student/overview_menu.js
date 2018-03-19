import React, { Component } from "react"
import {Link} from "react-router"

class OverviewMenu extends Component {
  render() {
    return (
      <div className="container">
        <div className="mt-4">
          <div className="nav justify-content-between">
            <div aria-label="breadcrumb">
              <ol className="breadcrumb">
                <li className="breadcrumb-item"><a href="#">Home</a></li>
                <li className="breadcrumb-item active" aria-current="page">General info</li>
              </ol>
            </div>
            <ul className="nav nav-pills">
              <li className="nav-item">
                <Link to="/home/general" className="nav-link active btn">General info</Link>
              </li>
              <li className="nav-item">
                <Link to="/home/documents" className="nav-link btn">Documents</Link>
              </li>
            </ul>
          </div>
        </div>
        {this.props.children}
      </div>
    )
  }
}

export default OverviewMenu
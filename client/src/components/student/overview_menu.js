import React, { Component } from 'react'

class OverviewMenu extends Component {
  render() {
    return (
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
              <a className="nav-link" href="#">General information</a>
            </li>
            <li className="nav-item">
              <a className="nav-link active" href="#">Documents</a>
            </li>
          </ul>
        </div>
      </div>
    )
  }
}

export default OverviewMenu
import React, { Component } from "react"

class TableRow extends Component {

  render() {
    const { student, index, openModal } = this.props
    const { firstName, middleName, lastName, birthDate, school, city, email, id } = student

    return (
      <li className="py-3" onClick={() => openModal(id)}>
        <div className="row text-center">
          <div className="col-lg-1 mb-1 mb-lg-0">
            <p>{ index }</p>
          </div>
          <div className="col-lg-2 mb-1 mb-lg-0">
            <p>{ firstName }</p>
          </div>
          <div className="col-lg-2 mb-1 mb-lg-0">
            <p>{ middleName }</p>
          </div>
          <div className="col-lg-2 mb-1 mb-lg-0">
            <p>{ lastName }</p>
          </div>
          <div className="col-lg-1 mb-1 mb-lg-0">
            <p>{ birthDate }</p>
          </div>
          <div className="col-lg-2 mb-1 mb-lg-0">
            <p>{ city }</p>
          </div>
          <div className="col-lg-2 mb-1 mb-lg-0">
            <p>{ school }</p>
          </div>
        </div>
      </li>
    )
  }
}

export default TableRow
import React, { Component } from "react"
import {message} from "../../locale/message";

class TableRow extends Component {

  render() {
    const { student, index, openModal, lang } = this.props
    const { firstName, middleName, lastName, iin, gender, generalStatus, id} = student
    const status_className = generalStatus.toUpperCase() === 'WAITING_FOR_RESPONSE' ? 'warning' :
      generalStatus.toUpperCase() === 'VALID' ? 'valid' : 'invalid'

    const statusColor = generalStatus.toUpperCase() === 'WAITING_FOR_RESPONSE' ? 'text-warning' :
      generalStatus.toUpperCase() === 'VALID' ? 'text-success' : 'text-danger'

    return (
      <li className={"py-3 " + status_className} onClick={() => openModal(id)}>
        <div className="row text-center">
          <div className="col-lg-1 mb-1 mb-lg-0 vertical-center">
            <p>{ index }</p>
          </div>
          <div className="col-lg-2 mb-1 mb-lg-0 vertical-center">
            <p>{ firstName }</p>
          </div>
          <div className="col-lg-2 mb-1 mb-lg-0 vertical-center">
            <p>{ middleName }</p>
          </div>
          <div className="col-lg-2 mb-1 mb-lg-0 vertical-center">
            <p>{ lastName }</p>
          </div>
          <div className="col-lg-2 mb-1 mb-lg-0 vertical-center">
            <p>{ iin }</p>
          </div>
          <div className="col-lg-1 mb-1 mb-lg-0 vertical-center">
            <p style={{wordBreak: "break-all"}}>{ message[gender.toLowerCase()][lang] }</p>
          </div>
          <div className="col-lg-2 mb-1 mb-lg-0 vertical-center">
            <p className="text-truncate">{ message[generalStatus.toLowerCase()][lang] }</p>
          </div>
        </div>
      </li>
    )
  }
}

export default TableRow
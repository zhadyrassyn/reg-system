import React, { Component } from "react"
import TableRow from "./table_row"
import _ from "lodash"

class TableBody extends Component {

  renderList = (students, startCounter, openModal) => {
    return _.map(students, student => (
      <TableRow
         student={ student }
         index={ ++startCounter }
         key={ student.id }
         openModal={openModal}
      />
    ))
  }


  render() {
    const { students, startCounter, openModal } = this.props

    return (
      <section className="table-body">
        <div className="container">
          <ul className="list-unstyled">
            {this.renderList(students, startCounter, openModal)}
          </ul>
        </div>
      </section>
    )
  }
}

export default TableBody
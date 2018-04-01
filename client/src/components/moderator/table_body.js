import React, { Component } from "react"
import TableRow from "./table_row"
import _ from "lodash"

class TableBody extends Component {

  renderList = (students) => {
    let index = 1;
    return _.map(students, student => (
      <TableRow
         student={ student }
         index={ index++ }
         key={ student.id }
      />
    ))
  }

  render() {
    const students = this.props.students

    return (
      <section className="table-body">
        <div className="container">
          <ul className="list-unstyled">
            {this.renderList(students)}
          </ul>
        </div>
      </section>
    )
  }
}

export default TableBody
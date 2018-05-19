package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.server.domain.Document
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class DocumentRepository(val jdbcTemplate: JdbcTemplate) {

    fun save(document: Document) : Long {
        val query = "INSERT INTO DOCUMENT(ud_back, ud_front, photo3x4, school_diploma, ent_certificate, form86, form63, flurography, user_id)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(counter++, document.ud_back)
                ps.setString(counter++, document.ud_front)
                ps.setString(counter++, document.photo3x4)
                ps.setString(counter++, document.school_diploma)
                ps.setString(counter++, document.ent_certificate)
                ps.setString(counter++, document.form86)
                ps.setString(counter++, document.form63)
                ps.setString(counter++, document.flurography)
                ps.setLong(counter, document.userId)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }
}
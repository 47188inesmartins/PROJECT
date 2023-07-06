package backend.jvm.dao

import backend.jvm.model.image.ImageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ImageDao: JpaRepository<ImageEntity, Int> {

    /**
     * Get all images of a company
     * @param imagePk_companyId
     * @return List<Image>
     */
    fun getImageByCompany_Id(company_id: Int):List<ImageEntity>
}
package tech.challenge.orderservice.application.presenters.mappers

import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import java.lang.reflect.InvocationTargetException

@Component
class GenericMapper {

    fun <T, U> toTransform(`object`: U, domainClass: Class<T>): T? {
        var domain: T? = null
        try {
            domain = domainClass.getDeclaredConstructor().newInstance()
            BeanUtils.copyProperties(`object`!!, domain!!)
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        return domain
    }

    fun <T, U> toTransformList(objectList: List<U>, domainClass: Class<T>?): List<T?> {
        val domainList: MutableList<T?> = ArrayList()
        for (`object` in objectList) {
            val domain = toTransform(`object`, domainClass!!)
            domainList.add(domain)
        }
        return domainList
    }
}
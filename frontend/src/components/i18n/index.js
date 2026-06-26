import Vue from 'vue'
import VueI18n from 'vue-i18n'

Vue.use(VueI18n)

// 各个国家的key
const localeKeys = ['en', 'zh-CN']

// 各个国家语言包
const messages = {}
for (const key of localeKeys) {
  messages[key] = require(`./locales/${key}/index.js`).default
}

export default new VueI18n({
  locale: 'en',
  messages,
  silentTranslationWarn: true // 忽略翻译警告
})

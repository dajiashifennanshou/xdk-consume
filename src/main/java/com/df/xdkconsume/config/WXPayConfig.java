package com.df.xdkconsume.config;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.df.xdkconsume.pay.IWXPayDomain;
import com.df.xdkconsume.pay.IWXPayDomainImpl;
@Component
public  class WXPayConfig {
	@Value("${pay-config.appid}")
	String appid;
	@Value("${pay-config.mch_id}")
	String mchid;
	@Value("${pay-config.key}")
	String key;

    /**
     * 获取 App ID
     *
     * @return App ID
     */
	public String getAppID() {
		return appid;
	}


    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
     public String getMchID() {
		return mchid;
	}


    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
     public String getKey() {
		return key;
	}


    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    public InputStream getCertStream() {
		return null;
	}

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpConnectTimeoutMs() {
        return 6*1000;
    }

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpReadTimeoutMs() {
        return 8*1000;
    }

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     * @return
     */
    public IWXPayDomain getWXPayDomain() {
		return new IWXPayDomainImpl();
	}

    /**
     * 是否自动上报。
     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
     *
     * @return
     */
    public boolean shouldAutoReport() {
        return true;
    }

    /**
     * 进行健康上报的线程的数量
     *
     * @return
     */
    public int getReportWorkerNum() {
        return 6;
    }


    /**
     * 健康上报缓存消息的最大数量。会有线程去独立上报
     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
     *
     * @return
     */
    public int getReportQueueMaxSize() {
        return 10000;
    }

    /**
     * 批量上报，一次最多上报多个数据
     *
     * @return
     */
    public int getReportBatchSize() {
        return 10;
    }

}

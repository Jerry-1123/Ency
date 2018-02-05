package com.xxx.ency.model.bean;

/**
 * Created by xiarh on 2017/10/13.
 */

public class UpdateBean {

    /**
     * name : Ency
     * version : 1
     * changelog : Init();
     * updated_at : 1507859742
     * versionShort : 1.0
     * build : 1
     * installUrl : http://download.fir.im/v2/app/install/59e01d00959d691da60001b0?download_token=98c5bb355d23be3821ae5721490f8296&source=update
     * install_url : http://download.fir.im/v2/app/install/59e01d00959d691da60001b0?download_token=98c5bb355d23be3821ae5721490f8296&source=update
     * direct_install_url : http://download.fir.im/v2/app/install/59e01d00959d691da60001b0?download_token=98c5bb355d23be3821ae5721490f8296&source=update
     * update_url : http://fir.im/Ency
     * binary : {"fsize":3682905}
     */

    private String name;
    private int version;
    private String changelog;
    private int updated_at;
    private String versionShort;
    private String build;
    private String installUrl;
    private String install_url;
    private String direct_install_url;
    private String update_url;
    private BinaryBean binary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getDirect_install_url() {
        return direct_install_url;
    }

    public void setDirect_install_url(String direct_install_url) {
        this.direct_install_url = direct_install_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public BinaryBean getBinary() {
        return binary;
    }

    public void setBinary(BinaryBean binary) {
        this.binary = binary;
    }

    public static class BinaryBean {
        /**
         * fsize : 3682905
         */

        private double fsize;

        public double getFsize() {
            return fsize;
        }

        public void setFsize(double fsize) {
            this.fsize = fsize;
        }
    }
}
